package domain.vehicle;

public class Vehicle {
	/* attribute */
	private int regNum;              
	private String transmission;         
	private String releaseDate;        
	private String vin;         
	private String condition;  
	private boolean is_sold;
	private String displacement;         
	private int price;                
	private int detailedModel;       
	private int registerer;           
	private String category;
	private String is_hybrid;    
	private int mpg;
	private int mileage; 
	
	
	public Vehicle(int regNum, int transmission, String releaseDate, String vin, int condition,String is_sold, int displacement, int price, int detailedModel, int registerer, int category, String is_hybrid, int mpg, int mileage) {
		this.regNum = regNum;
		this.transmission = this.typeTransmission(transmission);
		this.releaseDate = releaseDate;
		this.vin = vin;
		this.condition = this.typeCondition(condition);
		if(is_sold.equals("TRUE")) {
			this.is_sold = true;
		} else {
			this.is_sold = false;
		}
		this.displacement = this.typeDisplacement(displacement);
		this.price = price;
		this.detailedModel = detailedModel;
		this.registerer = registerer;
		this.category = this.typeCategory(category);
		if(is_hybrid.equals("TRUE")) {
			this.is_hybrid = "YES";
		} else {
			this.is_hybrid = "NO";
		}
		
		this.mpg = mpg;
		this.mileage = mileage;
	}
	
	
	/* method */
	private String typeTransmission(int transmission) {
		if (transmission == 1)
			return "Auto";
		else if (transmission == 2)
			return "Manual";
		else if (transmission == 3)
			return "Semi-Auto";
		else
			return "CVT";
	}
	
	private String typeCondition(int condition) {
		if (condition == 1)
			return "Excellent";
		else if (condition == 2)
			return "Good";
		else if (condition == 3)
			return "Fair";
		else
			return "Poor";
	}
	
	private String typeDisplacement(int displacement) {
		if (displacement == 1)
			return "less than 500";
		else if (displacement == 2)
			return "less than 1,000";
		else if (displacement == 3)
			return "less than 1,500";
		else if (displacement == 4)
			return "less than 2,000";
		else if (displacement == 5)
			return "less than 2,500";
		else if (displacement == 6)
			return "less than 3,000";
		else if (displacement == 7)
			return "less than 3,500";
		else if (displacement == 8)
			return "less than 4,000";
		else
			return "more than 4,000";
	}
	
	private String typeCategory(int category) {
		if (category == 1)
			return "Small";
		else if (category == 2)
			return "Semi-medium";
		else if (category == 3)
			return "Medium";
		else if (category == 4)
			return "Semi-large";
		else if (category == 5)
			return "Large";
		else if (category == 6)
			return "Sport-car";
		else if (category == 7)
			return "SUV";
		else
			return "Etc";		
	}
	
	
	/* getter */
	public int getRegNum() {
		return regNum;
	}

	public String getTransmission() {
		return transmission;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getVin() {
		return vin;
	}

	public String getCondition() {
		return condition;
	}
	
	public boolean getIs_sold() {
		return is_sold;
	}

	public String getDisplacement() {
		return displacement;
	}

	public int getPrice() {
		return price;
	}

	public int getDetailedModel() {
		return detailedModel;
	}

	public int getRegisterer() {
		return registerer;
	}

	public String getCategory() {
		return category;
	}

	public String getIs_hybrid() {
		return is_hybrid;
	}

	public int getMpg() {
		return mpg;
	}

	public int getMileage() {
		return mileage;
	}

	
}
