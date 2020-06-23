package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class SectionRepositoryTest implements TestData {

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    public void givenNothing_whenSaveSection_thenFindListWithOneElement() {
        Section section = Section.builder()
            .id(ID)
            .name(FNAME)
            .description(DESC)
            .capacity(TOTAL)
            .build();

        sectionRepository.save(section);

        assertEquals(1, sectionRepository.findAll().size());
    }
}
