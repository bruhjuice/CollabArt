package util;

import java.util.ArrayList;
import java.util.Random;

public class PromptPool
{
	// how is this gonna work? Will need to correspond with Elliot / where we will init json data
   public static ArrayList<Prompt> prompts = new ArrayList<Prompt>(){
         //use json parser
   
   };
   
   
   public static Prompt getPrompt()
   {
	   Random random = new Random();
	   int upperLimit = prompts.size();
	   if (prompts.size() == 0)
	   {
		   return null;
	   }
	   else
	   {
		   return prompts.get(random.nextInt(upperLimit));
	   }   
   }
   
}