package util.enumeration;

public enum Gender {
    MALE, FEMALE;

    public static Gender getEnumGender(String gen) {
        if(gen.equalsIgnoreCase("Male")){
            return MALE;
        }else if(gen.equalsIgnoreCase("Female")){
            return FEMALE;
        }
        return null;
    }
}