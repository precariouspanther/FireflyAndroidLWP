package net.precariouspanther.gdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Config {
   public static final Preferences preferences = Gdx.app.getPreferences("preferences");

   public static boolean parameter = true;

   public static void load() {
      parameter = preferences.getBoolean("parameter", true);
   }

   public static void save() {
      preferences.putBoolean("pamareter", parameter);
      preferences.flush();
   }
}