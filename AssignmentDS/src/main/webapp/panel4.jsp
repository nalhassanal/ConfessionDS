<%-- 
    Document   : panel4
    Created on : Jun 21, 2022, 4:02:03 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>UM Confession Website | SusScorpion</title>
        <link rel="stylesheet" href="style.css"/>
        
    </head>
    <body>
        <header>
          <ul>
            
            <li><span><a class="a2" href="panel1.jsp">Back to Main Menu</a></span></li>
          </ul>
            <div class="primary_header">
                <div class="title">
                    <div>
			<h1> UM Confession</h1>
                        <h2>@LuahanUniversitiMalaya</h2>
                    </div>
				</div>
			</div>
    </header>
    <section class="searchconfession">
		<div class="searchleft">Search Confession Post ID #</div>
	<div class="searchright">
		<form>
		  <input type="searchid" class="input_boxsearch">
		</form>
	</div>
	<div class="searchthird">
		<span id="search" class="searchidbutton">Search</span>
	</div>
	</section>
		<table id="show" class="confessiontable">
				<tr>
					<th>Post ID</th>
					<th>Date</th>
					<th>Time</th>
					<th>Confession</th>

				</tr>
			
		</table>
		<div class="copyright"></div>
		</body>
</html>
