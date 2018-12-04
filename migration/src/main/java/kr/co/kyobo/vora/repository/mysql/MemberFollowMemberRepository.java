package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.MemberAndMemberIdx;
import kr.co.kyobo.vora.model.database.mysql.MemberFollowMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFollowMemberRepository extends JpaRepository<MemberFollowMember, MemberAndMemberIdx> {

}
