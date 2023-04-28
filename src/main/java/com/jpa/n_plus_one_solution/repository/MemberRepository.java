package com.jpa.n_plus_one_solution.repository;

import com.jpa.n_plus_one_solution.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.orders o WHERE m.memberId = :memberId")
    public List<Member> findMemberAndOrder(Long memberId);
}
