package com.spring.data.bug.demo.repo;

import com.spring.data.bug.demo.DemoApplication;
import com.spring.data.bug.demo.model.Language;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@Transactional
public class LanguageRepositoryTest {

    @Autowired
    private JpaRepository<Language, String> languageRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void testFindAllPaginated_success() {

        Page<Language> languagePage = languageRepository.findAll(new Pageable() {
            @Override
            public int getPageNumber() {
                return 2;
            }

            @Override
            public int getPageSize() {
                return 3;
            }

            @Override
            public long getOffset() {
                return 1;
            }

            @Override
            public Sort getSort() {
                return Sort.by(Sort.Direction.ASC, "languageId");
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });

        /**
         * offset: 1
         * pageNo: 2 (0-based)
         * pageSz: 3
         * order: asc by languageId
         * data: lang0, lang1, lang2, lang3, lang4, lang5, lang6, lang7, lang8, lang9
         * expected: lang7, lang8, lang9
         */
        SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(languagePage).hasSize(3);
                    softly.assertThat(languagePage)
                          .extracting("languageId")
                          .containsExactly("lang7", "lang8", "lang9");
                }
        );
    }

}
