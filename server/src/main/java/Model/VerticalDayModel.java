package Model;

/**
 * Represent VerticalDayModel with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class VerticalDayModel {
  private String pk;
  private int total;

  public VerticalDayModel(int skierID, int resortID, int seasonID, int dayID, int vertical) {
    this.pk = skierID + "/" + resortID + "/" + seasonID + "/" + dayID;
    this.total = vertical;
  }

  public String getPk() {
    return pk;
  }

  public void setPk(String pk) {
    this.pk = pk;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
