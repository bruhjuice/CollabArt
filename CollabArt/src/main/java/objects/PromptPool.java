package objects;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.cj.xdevapi.JsonParser;


public class PromptPool
{
   public static ArrayList<Prompt> prompts = new ArrayList<Prompt>();
   
   public static void init() {
      JSONParser parser = new JSONParser();
      InputStream stream = PromptPool.class.getResourceAsStream("prompts.json");
      try (Reader reader = new InputStreamReader(stream, "UTF-8")) {
         //Parse entire JSON
         Object obj = parser.parse(reader);
         JSONArray jsonArray = (JSONArray ) obj;
         // Iterate through each Prompt object 
         Iterator<JSONObject> iter = jsonArray.iterator();
         while (iter.hasNext()) {
            JSONObject currPrompt = iter.next();
            //Statement
            String statement=(String) currPrompt.get("statement");
            
            //Array of Individual prompts
            ArrayList<String> prompts=new ArrayList<String>();
            JSONArray promptsArray = (JSONArray)currPrompt.get("prompts");
            Iterator<String> promptiter = promptsArray.iterator();
            while(promptiter.hasNext()) {
               prompts.add(promptiter.next());
            }
            
            //Array of coordinates
            ArrayList<Coordinate> coordinates=new ArrayList<Coordinate>();
            JSONArray CoordinatesArray = (JSONArray)currPrompt.get("coordinates");
            Iterator<JSONObject> coorditer = CoordinatesArray.iterator();
            while(coorditer.hasNext()) {
               JSONObject coord = coorditer.next();
               long top = (Long)coord.get("top");
               long left = (Long)coord.get("left");
               long bottom = (Long)coord.get("bottom");
               long right = (Long)coord.get("right");
               
               Coordinate coor = new Coordinate((int)top,(int)left,
                     (int)bottom,(int)right);
               coordinates.add(coor);
            }
            
            //Background image path
            String background_image_path = (String) currPrompt.get("background_image_path");
            
            //Add new prompt into prompt pool
            Prompt newPrompt = new Prompt(statement, prompts, coordinates, background_image_path);
            System.out.println(newPrompt);
            PromptPool.prompts.add(newPrompt);
         } 

     } catch (IOException e) {
         e.printStackTrace();
     } catch (ParseException e){
      e.printStackTrace();
     }

      System.out.println(PromptPool.prompts);
   }

   public static Prompt getPrompt()
   {
      Random random = new Random();
      int upperLimit = prompts.size();
      if (prompts.size() == 0)
      {
         return null;
      } else
      {
         return prompts.get(random.nextInt(upperLimit));
      }
   }

}