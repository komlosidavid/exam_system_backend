
package inf.unideb.hu.exam.system.models;

import inf.unideb.hu.exam.system.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionTest {

    @Autowired
    private QuestionDao questionDao;


}