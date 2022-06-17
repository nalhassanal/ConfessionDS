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
    public static void main(String[] args) {
        String [] input = {"hey do you want to go to a movie tonight?" , "gay", "test"};
        for(String in : input){
            boolean spam = predictSpam(in.split(" ") , true);
            System.out.println(spam);
        }
    }

    public static boolean predictSpam(String [] ls, boolean verbose){
        return readAndPrepareData(ls, verbose);

    }

    public static boolean readAndPrepareData(String [] ls, boolean verbose){
        Queue<String> temp_df = new LinkedList<>();
        String fileName = "\\Users\\Hassanal\\Java\\ConfessionDS\\AssignmentDS\\src\\main\\java\\spam\\spam.csv";
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
        // return true;
    }

    public static boolean predictText(Map<String, Double> train_spam_bow, Map<String, Double> train_non_spam_bow, String [] ls, boolean verbose, double FRAC_SPAM_TEXT){
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

        // spam_score =
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



    public static Set<String> setOf(ArrayList<String> values){
        return new HashSet<>(values);
    }

    public static String removePunctuations(String source){
        return source.replaceAll("\\p{Punct}", "");
    }
}
