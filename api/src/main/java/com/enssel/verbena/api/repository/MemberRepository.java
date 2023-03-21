package com.enssel.verbena.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enssel.verbena.api.model.TestNougat0;

import jakarta.transaction.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<TestNougat0, String> {
	
	List<TestNougat0> findByUseYn(String useYn);
	
	List<TestNougat0> findByUserId(String userId);
	
//	List<TestNougat0> findAllByUserNm(String userNm);
	
//	@Modifying
//	@Transactional
//	@Query(value="UPDATE test.test.TEST_NOUGAT0 "
//			+ "SET USE_YN = 'N' "
//			+ "WHERE USER_ID = :userId",
//			nativeQuery=true)
//	int deleteByUserYn(
//			@Param("userId") String userId
//			);
//	
//	@Modifying
//	@Transactional
//	@Query(value="UPDATE test.test.TEST_NOUGAT0 "
//			+ "SET "
//			+ "USER_ID = :userIdAfter "
//			+ ",USER_NM = :userNm "
//			+ ",UPDA_USER = :updaUser "
//			+ ",PW = :pw "
//			+ ",UPDA_DT = GETDATE() "
//			+ "WHERE USER_ID = :userIdBefore ",
//			nativeQuery=true)
//	int updateOneMember(
//			@Param("userIdAfter") String userIdAfter
//			,@Param("userNm") String userNm
//			,@Param("updaUser") String updaUser
//			,@Param("pw") String pw
//			,@Param("userIdBefore") String userIdBefore
//			);
//	
//	@Modifying
//	@Transactional
//	@Query(value="SELECT * FROM test.test.TEST_NOUGAT0 "
//			+ "WHERE USE_YN = 'Y' ",
//			nativeQuery=true)
//	List<TestNougat0> selectAllMemberWithY();	
//	
//	
}
