

public class PatientMatchingSystem {

    public enum MatchDecision {
        AUTO_MATCH, 
        REVIEW,     
        NO_MATCH    
    }

    public static class PatientRecord {
        String name;
        String dob; 
        String phone;
        String email;

        public PatientRecord(String name, String dob, String phone, String email) {
            this.name = name;
            this.dob = dob;
            this.phone = phone;
            this.email = email;
        }
    }

  
    public MatchDecision evaluateMatch(PatientRecord existing, PatientRecord incoming) {
        String normName = normalizeText(existing.name);
        String normNameInc = normalizeText(incoming.name);
        
        String normDob = normalizeText(existing.dob);
        String normDobInc = normalizeText(incoming.dob);
        
        String normPhone = normalizePhone(existing.phone);
        String normPhoneInc = normalizePhone(incoming.phone);
        
        String normEmail = normalizeText(existing.email);
        String normEmailInc = normalizeText(incoming.email);

        int matchCount = 0;
        
        if (isMatch(normName, normNameInc)) matchCount++;
        if (isMatch(normDob, normDobInc)) matchCount++;
        if (isMatch(normPhone, normPhoneInc)) matchCount++;
        if (isMatch(normEmail, normEmailInc)) matchCount++;

        if (matchCount >= 3) {
            return MatchDecision.AUTO_MATCH; 
        } else if (matchCount == 2) {
            return MatchDecision.REVIEW;
        } else {
            return MatchDecision.NO_MATCH;
        }
    }

    private String normalizeText(String input) {
        if (input == null) return "";
        return input.trim().toLowerCase();
    }

    private String normalizePhone(String input) {
        if (input == null) return "";
        return input.replaceAll("[^0-9]", "");
    }

    private boolean isMatch(String s1, String s2) {
        return !s1.isEmpty() && s1.equals(s2);
    }

    public static void main(String[] args) {
        PatientMatchingSystem system = new PatientMatchingSystem();

        PatientRecord existing = new PatientRecord(
            "John Doe", "1990-05-15", "0812-3456-7890", "john.doe@mail.com"
        );

        // SAMPLE INPUT 1: REVIEW
        PatientRecord input1 = new PatientRecord(
            "JOHN DOE ", "1990-05-15", "0811-2222-3333", "new_john@mail.com"
        );
        MatchDecision result1 = system.evaluateMatch(existing, input1);
        System.out.println("Decision 1: " + result1);

        System.out.println();

        // SAMPLE INPUT 2: AUTO_MATCH
        PatientRecord input2 = new PatientRecord(
            "john doe", "1990-01-01", "081234567890", "JOHN.DOE@MAIL.COM"
        );
        MatchDecision result2 = system.evaluateMatch(existing, input2);
        System.out.println("Decision 2: " + result2);
    }
}