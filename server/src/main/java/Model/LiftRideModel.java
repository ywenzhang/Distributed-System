package Model;

/**
 * Represent LiftRideModel with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class LiftRideModel {
  private int skierId;
  private int resortId;
  private int seasonId;
  private int dayId;
  private int time;
  private int liftId;
  private String PK;

  public LiftRideModel(int skierId, int resortId, int seasonId, int dayId, int time, int liftId, String PK) {
    this.skierId = skierId;
    this.resortId = resortId;
    this.seasonId = seasonId;
    this.dayId = dayId;
    this.time = time;
    this.liftId = liftId;
    this.PK = PK;
  }

  public int getSkierId() {
    return skierId;
  }

  public void setSkierId(int skierId) {
    this.skierId = skierId;
  }

  public int getResortId() {
    return resortId;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  public int getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(int seasonId) {
    this.seasonId = seasonId;
  }

  public int getDayId() {
    return dayId;
  }

  public void setDayId(int dayId) {
    this.dayId = dayId;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public int getLiftId() {
    return liftId;
  }

  public void setLiftId(int liftId) {
    this.liftId = liftId;
  }

  public String getPK() {
    return PK;
  }

  public void setPK(String PK) {
    this.PK = PK;
  }

  @Override
  public String toString() {
    return "LiftRideModel{" +
        "skierId=" + skierId +
        ", resortId=" + resortId +
        ", seasonId=" + seasonId +
        ", dayId=" + dayId +
        ", time=" + time +
        ", liftId=" + liftId +
        '}';
  }
}
