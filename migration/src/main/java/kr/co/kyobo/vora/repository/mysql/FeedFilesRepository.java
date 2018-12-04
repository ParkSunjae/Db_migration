package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.FeedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedFilesRepository extends JpaRepository<FeedFiles, Integer> {

}