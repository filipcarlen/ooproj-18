package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * This Class handels the input from the keyboard
 * @author Project Group 18 (Chalmers, 2013)
 *
 */
public class Controls {
	/*
	 * This for Strings are the diffrent commands you can do
	 */
	public static final String CMD_LEFT = "left";
	public static final String CMD_RIGHT = "right";
	public static final String CMD_JUMP = "jump";
	public static final String CMD_FIGHT = "fight";
	private static final String CMD_PAUSE = "pause";
	
	private boolean isControlsSet;
	
	private Input input;
	
	/*
	 * This HashMap is used to place one or two keys to the same Action 
	 */
	private HashMap<String, List<Integer>>keys = new HashMap<String, List<Integer>>();
	
	/**
	 * A instance of Controls
	 */
	private static Controls instanceOfControls = null;
	
	/**
	 * By default this Constructor will add the command Pause
	 */
	private Controls(){
		loadCommands();
		try {
			bindToKey(CMD_PAUSE, Input.KEY_ESCAPE, Input.KEY_P);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		isControlsSet = false;
	}
	
	/**
	 * @return - A Instance of Controls
	 */
	public static Controls getInstance(){
		if(instanceOfControls == null){
			instanceOfControls = new Controls();
		}
		return instanceOfControls;
	}
	
	/**
	 * Loads all the Commands that is declared.
	 */
	public void loadCommands(){
		keys.put(CMD_LEFT, new ArrayList<Integer>());
		keys.put(CMD_RIGHT, new ArrayList<Integer>());
		keys.put(CMD_JUMP, new ArrayList<Integer>());
		keys.put(CMD_FIGHT, new ArrayList<Integer>());
		keys.put(CMD_PAUSE, new ArrayList<Integer>());
	}
	
	/**
	 * This Method gives Controls a input from the Keyboard.
	 * @param input
	 */
	public void updateInput(Input input){
		this.input = input;
	}
	
	/**
	 * This is a method to give the default setting to the key inputs
	 */
	public void setDeafaultControls(){
		try {
		bindToKey(CMD_LEFT, Input.KEY_A, Input.KEY_LEFT);
		bindToKey(CMD_RIGHT, Input.KEY_D, Input.KEY_RIGHT);
		bindToKey(CMD_FIGHT, Input.KEY_SPACE);
		bindToKey(CMD_JUMP, Input.KEY_W, Input.KEY_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this removes all the settings the key bindings
	 */
	public void removeKeySettings(){
		keys.clear();
		isControlsSet = false;
	}
	
	/**
	 * here you bind a keys to the different functions
	 * @param s - the name of a command
	 * @param key - Gives the integer to the key 
	 * @throws SlickException - when a string that isn't use as a command is put in method
	 */
	public void bindToKey(String s, int key) throws SlickException{
		if(s.toLowerCase().equals(CMD_LEFT)){
			keys.get(CMD_LEFT).add(key);
		}else if(s.toLowerCase().equals(CMD_RIGHT)){
			keys.get(CMD_RIGHT).add(key);
		}else if(s.toLowerCase().equals(CMD_JUMP)){
			keys.get(CMD_JUMP).add(key);
		}else if(s.toLowerCase().equals(CMD_FIGHT)){
			keys.get(CMD_FIGHT).add(key);
		}else if(s.toLowerCase().equals(CMD_PAUSE)){
			keys.get(CMD_PAUSE).add(key);
		}else{
			throw new SlickException("Unable to load the Command: " + s 
					+ "\nWith the keys\n1: " + Input.getKeyName(key));
		}
		isControlsSet= true;
	}
	
	/**
	 * here you bind a keys to the different functions
	 * @param s - the name of a command
	 * @param key - Gives the integer to the key 
	 * @throws SlickException - when a string that isn't use as a command is put in method
	 */
	public void bindToKey(String s, int key, int key1) throws SlickException{
		bindToKey(s, key);
		bindToKey(s, key1);
	}
	
	/**
	 * 
	 * @param s - the command
	 * @return true- if the string is binded to a key
	 * 			false- if the string isn't binded to a key
	 */
	public boolean check(String s){
		if(!s.equals(CMD_JUMP) && !s.equals(CMD_FIGHT) && !s.equals(CMD_PAUSE)){
			for(int i = 0; i < keys.get(s).size(); i++){
				if(input.isKeyDown(keys.get(s).get(i)))
					return input.isKeyDown(keys.get(s).get(i));
			}
		}
		for(int i = 0; i < keys.get(s).size(); i++){
			if(input.isKeyDown(keys.get(s).get(i)))
				return input.isKeyPressed(keys.get(s).get(i));
		}
		return false;
	}
	
	public boolean isControlsSet(){
		return isControlsSet;
	}
}
