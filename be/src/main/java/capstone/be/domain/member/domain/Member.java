package capstone.be.domain.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String snsId;


//    회원가입시 입력할 정보 4개
    @Setter
    @Column(nullable = false, length = 20)
    private String nickname;

    @Setter
    @Column(nullable = false)
    private boolean employed;

//    Todo : 희망분야  Enum으로 구현?
//    @Setter
//    private Category category;

    @Setter
    private int desiredSalary;

//  Todo : 연령, 성별 선택사항 필드 추가



}
