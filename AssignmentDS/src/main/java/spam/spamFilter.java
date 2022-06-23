package spam;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class spamFilter{

    /**
     * the starter method of the spamFilter class
     * @param ls the array of the input words
     * @param verbose to set verbosity which allows some process to be printed
     * @return true if the input text is spam, false otherwise
     */
    public static boolean predictSpam(String [] ls, boolean verbose){
        return readAndPrepareData(ls, verbose);
    }

    /**
     * to read the spam.csv file and then manipulate it to a bag of spam word probabilities
     * @param ls the array of the input words
     * @param verbose to set verbosity which allows some process to be printed
     * @return true if the input text is spam, false otherwise
     */
    public static boolean readAndPrepareData(String[] ls, boolean verbose){
        Queue<String> temp_df = new LinkedList<>();
        String fileName = ".\\dataFiles\\spam.csv";
        try{
            FileInputStream is = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.ISO_8859_1);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                int index = line.indexOf(",");

                String typeName = line.substring(0, index);
                if (typeName.equals("v1"))
                    continue;
                if (typeName.equals("ham"))
                    typeName = "0";
                else if (typeName.equals("spam"))
                    typeName = "1";

                String content = removePunctuations(line.substring(index));
                if ( content.equals("v2"))
                    continue;
                temp_df.offer(typeName);
                temp_df.offer(content);
            }
            br.close();
        } catch(Exception ex){ex.printStackTrace();}

        ArrayList<String> spam_df = new ArrayList<>(temp_df);

        ArrayList<String> train_spam_df = new ArrayList<>();
        for (int i = 0; i < 7804 ; i++){
            train_spam_df.add(spam_df.get(i));
        }

        ArrayList<String> test_spam_df = new ArrayList<>();
        for (int i = (spam_df.size() - 7804); i < spam_df.size() ; i++){
            test_spam_df.add(spam_df.get(i));
        }

        ArrayList<Integer> hamSpam = new ArrayList<>();
        for (int i = 0; i<train_spam_df.size();i++){
            if (i%2 == 0)
                hamSpam.add(Integer.parseInt( train_spam_df.get(i)));
        }
        int size = hamSpam.size();
        int sum = 0;
        for (Integer integer : hamSpam) sum = sum + integer;
        double FRAC_SPAM_TEXT = (double)sum/(double)size;

        /*
         * Create spam bag of words and non-spam bag of words
         */

        ArrayList<String> train_spam_words = new ArrayList<>();
        ArrayList<String> train_non_spam_words = new ArrayList<>();

        for (int i = 0; i<train_spam_df.size();i++){
            if (i%2 == 0){
                String line = train_spam_df.get(i);
                if (line.equals("0")){
                    String nextLine = train_spam_df.get(i+1);
                    String [] words = nextLine.split(" ");
                    for (String w : words){
                        if (w.isBlank())
                            continue;
                        train_non_spam_words.add(w);
                    }
                }
                else if (line.equals("1")){
                    String nextLine = train_spam_df.get(i+1);
                    String [] words = nextLine.split(" ");
                    for (String w : words){
                        if (w.isBlank())
                            continue;
                        train_spam_words.add(w);
                    }
                }
            }
        }

        /*
         * getting common words between two sets
         */
        Set<String> set1 = setOf(train_spam_words);
        Set<String> set2 = setOf(train_non_spam_words);
        Set<String> common_words = new HashSet<>(set1);
        common_words.retainAll(set2);

        Map<String , Double> train_spam_bow = new HashMap<>();

        for (String word: common_words){
            train_spam_bow.put(word, (double) Collections.frequency(train_spam_words, word) / (double) train_spam_words.size());
        }

        Map<String , Double> train_non_spam_bow = new HashMap<>();

        for (String word: common_words){
            train_non_spam_bow.put(word, (double) Collections.frequency(train_non_spam_words, word) / (double) train_non_spam_words.size());
        }

        return predictText(train_spam_bow, train_non_spam_bow , ls, verbose, FRAC_SPAM_TEXT);
    }

    /**
     * the method that actually predicts whether the text is spam or not
     * @param train_spam_bow the bag of spam probabilities
     * @param train_non_spam_bow the bag of non spam probabilities
     * @param ls the array of the input words
     * @param verbose to set verbosity which allows some process to be printed
     * @param FRAC_SPAM_TEXT the fraction of spam probabilities over non spam probabilities
     * @return true if the spamScore is more than or equal to nonSpamScore, false otherwise
     */
    public static boolean predictText(Map<String, Double> train_spam_bow,
                                      Map<String, Double> train_non_spam_bow, String[] ls,
                                      boolean verbose, double FRAC_SPAM_TEXT){
        double spam_score, non_spam_score;

        ArrayList<String> valid_words = new ArrayList<>();

        for (String element: ls)
            if(train_spam_bow.containsKey(element))
                valid_words.add(element);

        ArrayList<Double> spam_probs = new ArrayList<>();

        for (String word : valid_words)
            if(train_spam_bow.containsKey(word))
                spam_probs.add(train_spam_bow.get(word));

        ArrayList<Double> non_spam_probs = new ArrayList<>();

        for (String word : valid_words)
            if(train_non_spam_bow.containsKey(word))
                non_spam_probs.add(train_non_spam_bow.get(word));

        double sum = 0.0;
        for (Double p : spam_probs){
            sum = sum + Math.log(p);
        }
        spam_score = sum + Math.log(FRAC_SPAM_TEXT);

        sum = 0.0;
        for (Double p : non_spam_probs){
            sum = sum + Math.log(p);
        }

        non_spam_score = sum + Math.log(FRAC_SPAM_TEXT);

        if (verbose){
            System.out.println("Spam Score: " + spam_score);
            System.out.println("Non-Spam Score: " + non_spam_score);
        }

        return spam_score >= non_spam_score;
    }


    /**
     * converts an arraylist into a Set object
     * @param values the values to be converted
     * @return a Set of String object
     */
    public static Set<String> setOf(ArrayList<String> values){
        return new HashSet<>(values);
    }

    /**
     * method to remove all punctuations in a word or sentence
     * @param source the string to be manipulated
     * @return a string with all it's punctuations removed
     */
    public static String removePunctuations(String source){
        return source.replaceAll("\\p{Punct}", "");
    }
}
