<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="https://kit.fontawesome.com/1a5a91d3bb.js" crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Irish+Grover&display=swap" rel="stylesheet">

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">

<title> Collabart | Home</title>
</head>
<body>
   <!-- Navbar HERE 
   NOTE: make it so it stays at top even when scrolling down the artworks
   -->
   
   <!-- Thoughts: doing likes/dislikes will be hard: whenever they click on it, need to send update to database
   In addition, need to keep track of each user's likes, dislikes, to make sure they can't like multiple times? -->
   
   
   
   <br>
   <br>
   <!-- Text, image, and like count will all be grabbed from database. Can also add unique id to each galart element -->
   <!-- Would we have to store all images? Even if we have local path, how would we store it? -->
   <div class="galart">
      <div class="galart-top blue top-rounded"> <p> Draw... a cat playing basketball </p> </div>
      <!-- Images should be a bit wider, maybe something like 3:2? -->
      <div class="galart-mid"><img class = galart-img src="images/cat_basketball.png"></div>
      <div class="galart-bottom bottom-rounded">
         <i class="fa-solid fa-thumbs-up"></i> <span>&emsp; 413 Likes &emsp;</span> 
         <i class="fa-solid fa-thumbs-down"></i> 
      </div>
   </div>
   
</body>
</html>