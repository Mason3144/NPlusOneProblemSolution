package com.jpa.n_plus_one_solution.jpa_tester;

import com.jpa.n_plus_one_solution.entities.Member;
import com.jpa.n_plus_one_solution.entities.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.util.List;

@Profile("n-plus-one-problem")
@Configuration
public class NPlusOneProblemConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaFetchStrategyRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            creatingTestRows();
//            problemExample(); // n+1 문제 발생
            joinFetchUse(); // 쿼리문에 join fetch 적용
        };
    }

    private void problemExample() {
        TypedQuery<Member> query =
        em.createQuery("SELECT m FROM Member m", Member.class);

        List<Member> findMembers = query.getResultList();
        System.out.println();

        findMembers.stream().forEach(member->{
            System.out.println(member.getName()+"의 오더의 갯수: " + member.getOrders().size());
            System.out.println();

        });
    }


    private void joinFetchUse(){

        //      회원이가진 주문의 수만큼 중복데이터 발생- distinct로 해결
//        TypedQuery<Member> query =
//             em.createQuery("SELECT m FROM Member m "+
//                     "JOIN FETCH m.orders o", Member.class);

        TypedQuery<Member> query =
                em.createQuery("SELECT DISTINCT m FROM Member m JOIN FETCH m.orders o", Member.class);

        List<Member> findMembers = query.getResultList();

        findMembers.stream().forEach(member->{
            System.out.println(member.getName()+"의 오더의 갯수: " + member.getOrders().size());
        });
    }

    private void creatingTestRows(){
        tx.begin();
        Member member = new Member("회원1");
        Member member2 = new Member("회원2");
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        member.addOrder(order1);
        member.addOrder(order2);
        member2.addOrder(order3);

        order1.addMember(member);
        order2.addMember(member);
        order3.addMember(member2);

        em.persist(member);
        em.persist(member2);
        em.persist(order1);
        em.persist(order2);
        em.persist(order3);
        tx.commit();

        em.clear(); // persistence context에서 생성된 엔티티들 지워줌
    }
}

