package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer> {

}
