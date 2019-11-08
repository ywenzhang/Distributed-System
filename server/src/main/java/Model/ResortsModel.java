package Model;

/**
 * Represent ResortsModel with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class ResortsModel {
  private String key;
  private int resortId;
  private int season;

  public ResortsModel(int resortId, int season) {
    this.resortId = resortId;
    this.season = season;
    this.key = resortId + "/" + season;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getResortId() {
    return resortId;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  public int getSeason() {
    return season;
  }

  public void setSeason(int season) {
    this.season = season;
  }
}
