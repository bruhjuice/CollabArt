# CollabArt 

Welcome to...

<img src="/images/CollabArtLogo_Ver2.png" width="280">

## Introduction

CollabArt is a four player real-time drawing game, where artistic and teamwork skills are put to the test. Every player is given a specific prompt and allotted only 60 seconds to showcase their creativity. When the timer's up, the drawings are combined into a singular masterpiece, where players can *admire* the outcome of synergy and skill.

Sounds intriguing? [Play the game!](https://collab--art.herokuapp.com/)

## Our Gallery

The CollabArt Gallery is a feature only available to [registered users](https://collab--art.herokuapp.com/register.jsp). Past artworks, the artists, and the date of cretion are displayed. Users can additionally upvote and downvote the art pieces in the gallery.

<img src="/images/gallery.png" width="280">

## Playing the Game

While drawing, users have the flexibility of alternating between brush sizes and color to best draw the prompt quickly and effectively. There are additional options for clearing the screen or submitting early. Once the timer is up or all four players have submitted, users can view the combined artwork alongside the full prompt.

<p float="left">
  <img src="/images/gameProgress.png" width="357"/> &nbsp;
  <img src="/images/gameEnd.png" width="350" /> 
</p>

## Built With
- Heroku
- JSP (Jakarta Server Pages)
- Java
- JavaScript
- HTML/CSS
- Canvas API
- ClearDB (mySQL)
- Graphics2D

## Local Deployment

If you wish to personally deploy:
1. Run the sql script contained in “SQLScripts/createTables.sql”
2. In Utility.java, change dbURL to localhost, and change username & password
accordingly.
3. Right click project "Run as > maven build..." and set Goals: to "package"
4. In the root CollabArt folder, run "java -jar target/dependency/webapp-runner.jar
target/*.war" in console
5. Visit http://localhost:8080


Notes:
If you wish to play locally (don't do this, get your friends and play instead, its more fun that way I promise!), you should open up 4 different tabs on a single computer and join the room with the given room code

## Planning

For a behind-the-scenes look at GUI / userflow planning, check out this [Figma!](https://www.figma.com/file/90mEHfhCsRZfcflHzfARxB/CollabArt?node-id=0%3A1) 


