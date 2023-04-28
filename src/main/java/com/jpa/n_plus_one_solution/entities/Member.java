package com.jpa.n_plus_one_solution.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }
    public void addOrder(Orders orders) {
        this.orders.add(orders);
    }
}
