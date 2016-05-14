package model;

/**
 * 
 */
public enum CityNames {
    ARKON ("A",1),
    BURGEN ("B",2),
    CASTRUM ("C",3),
    DORFUL ("D",4),
    ESTI ("E",5),
    FRAMEK ("F",6),
    GRADEN ("G",7),
    HELLAR ("H",8),
    INDUR ("I",9),
    JUVELAR ("J",10),
    KULTOS ("K",11),
    LYRAM ("L",12),
    MERKATIM ("M",13),
    NARIS ("N",14),
    OSIUM ("O",15),
    PEHEATH ("P",16),
    QUARNGATE ("Q",17),
    REWOOD ("R",18),
    STEWOLDS ("S",19),
    TEEWICH ("T",20),
    URUKHAI ("U",21),
    VANHELSING ("V",22),
    WOOWICK ("W",23),
    XERO ("X",24),
    YUMA ("Y",25),
    ZAMAN ("Z",26),
    ΩMEGA ("Ω",27);
	
	
	private String firstCityLetter;
	private int cityNumbers;
	
	 CityNames(String firstCityLetter,int cityNumbers){
		this.firstCityLetter=firstCityLetter;
		this.cityNumbers=cityNumbers;
	}
	
	
}