package com.jpa.n_plus_one_solution.jpa_tester;

import com.jpa.n_plus_one_solution.entities.Member;
import com.jpa.n_plus_one_solution.entities.Orders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Profile("n-plus-one-problem")
@EntityScan(basePackageClasses = {NPlusOneProblemConfig.class})
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
        };
    }

    private void creatingTestRows(){
        tx.begin();
        Member member = new Member("Mason");
        Member member2 = new Member("Henry");
        Orders orders1 = new Orders();
        Orders orders2 = new Orders();
        Orders orders3 = new Orders();
        member.addOrder(orders1);
        member.addOrder(orders2);
        member2.addOrder(orders3);

        orders1.addMember(member);
        orders2.addMember(member);
        orders3.addMember(member2);

        em.persist(member);
        em.persist(member2);
        em.persist(orders1);
        em.persist(orders2);
        em.persist(orders3);
        tx.commit();

        em.clear();
    }


    private void fetchJoinJPQLTest() throws InterruptedException {

    }
}

