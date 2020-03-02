package domain.order;

public class Order {
	
	/* attribute */
	private int orderNum;
	private String orderDate;
	private int buyer;
	private int seller;
	private int regNum;

	public Order(int orderNum, String orderDate, int buyer, int seller, int regNum) {
		this.orderNum = orderNum;
		this.orderDate = orderDate;
		this.buyer = buyer;
		this.seller = seller;
		this.regNum = regNum;
	}
	
	/* getter and setter */
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getBuyer() {
		return buyer;
	}

	public void setBuyer(int buyer) {
		this.buyer = buyer;
	}

	public int getSeller() {
		return seller;
	}

	public void setSeller(int seller) {
		this.seller = seller;
	}
	
	public int getRegNum() {
		return regNum;
	}

	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}
}
