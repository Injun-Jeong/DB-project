package vehiclePackage;

public class briefVehicle {
	int reg_num;
	String make;
	String model;
	String detail;
	String category;
	int price;
	boolean is_sold;
	String release_date;
	int mileage;
	
	
	public briefVehicle(int reg_num, String make, String model, String detail, String category, int price,String is_sold, String release_date, int mileage) {
		this.reg_num = reg_num;
		this.make = make;
		this.model = model;
		this.detail = detail;
		this.category = category;
		this.price=price;
		if(is_sold.equals("TRUE"))
			this.is_sold=true;
		else
			this.is_sold=false;
		this.release_date = release_date;
		this.mileage = mileage;
	}

	public int getReg_num() {
		return reg_num;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getDetail() {
		return detail;
	}

	public String getCategory() {
		return category;
	}

	public int getPrice() {
		return price;
	}
	public boolean getIs_sold() {
		return is_sold;
	}

	public String getRelease_date() {
		return release_date;
	}

	public int getMileage() {
		return mileage;
	}
	
}