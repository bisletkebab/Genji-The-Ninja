package helpers;

/**
 * Created by Per on 22.09.2016.
 */
public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    //public String getPlayerHighscore();
    public boolean isSignedIn();
}
