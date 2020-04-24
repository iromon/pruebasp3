package etsisi.ems2020.trabajo3.lineadehorizonte;

public class Edificio {
	private int xi;
    private int y;
    private int xd;
    
    public Edificio()
    {
        this.xd = 0;
        this.xi = 0;
        this.y = 0;
    }    
    public Edificio(int xi, int y, int xd)
    {
        this.xd = xd;
        this.xi = xi;
        this.y = y;
    }    
    public int getXi() {
        return this.xi;
    }
    public void setXi(int xi) {
        this.xi = xi;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getXd() {
        return this.xd;
    }
    public void setXd(int xd) {
        this.xd = xd;
    }
}
