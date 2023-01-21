package capstone.be.domain.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String nickname;

//    재직여부
    @Setter
    private boolean employed;

////    희망분야  Enum으로 구현?
//    @Setter
//    private Category category;

    @Setter
    private int desiredSalary;
    
}
