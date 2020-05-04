package ee.mrtnh.inbank_test_assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(unique = true)
    private long personalCode;
    private int segmentCode;

    public Client(long personalCode, int segmentCode) {
        this.personalCode = personalCode;
        this.segmentCode = segmentCode;
    }

    public double getCreditModifier() {
        if (segmentCode == 0) return 0;
        else if (segmentCode == 1) return 100;
        else if (segmentCode == 2) return 300;
        else if (segmentCode == 3) return 1000;
        return -1; // faulty client data, no lending approved
    }
}


