package com.basejava.webapp.storage;

import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name_1");
        RESUME_2 = new Resume(UUID_2, "Name_2");
        RESUME_3 = new Resume(UUID_3, "Name_3");
        RESUME_4 = new Resume(UUID_4, "Name_4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_1, "Name_1");
        storage.update(updateResume);
        assertEquals(updateResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_4, "Name_4"));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        try {
            storage.delete(UUID_1);
        } catch (NotExistStorageException e) {
            Assert.fail("Удаление осуществляется не корректно!");
        }
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void testFullResume () {
        Resume testResume = ResumeTestData.forTestMethod(UUID_1, "Name");
        assertEquals(UUID_1, testResume.getUuid());
        assertEquals("Name", testResume.getFullName());
        assertEquals("+79998887766", testResume.getContants(ContactType.PHONE));
        assertEquals("+74959995566", testResume.getContants(ContactType.HOME_PHONE));
        assertEquals("contact@mail.ru", testResume.getContants(ContactType.E_MAIL));
        assertEquals("https://www.linkedin.com/in/contact", testResume.getContants(ContactType.LINKEDIN));
        assertEquals("https://github.com/contact", testResume.getContants(ContactType.GITHUB));
        assertEquals("https://stackoverflow.com/users/56565656", testResume.getContants(ContactType.STACKOVERFLOW));
        assertEquals("http://contact.ru/", testResume.getContants(ContactType.HOME_PAGE));

        assertEquals("Аналитический склад ума, сильная логика, креативность, инициативность.", testResume.getSection(SectionType.PERSONAL).toString());
        assertEquals("Ведущий стажировок", testResume.getSection(SectionType.OBJECTIVE).toString());
        ListSection achievement = new ListSection();
        achievement.setContent("С 2013 года: разработка проектов \"Разработка Web приложения\"");
        achievement.setContent("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike");
        assertEquals(achievement, testResume.getSection(SectionType.ACHIEVEMENT));
        ListSection qualification = new ListSection();
        qualification.setContent("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.setContent("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        assertEquals(qualification, testResume.getSection(SectionType.QUALIFICATIONS));

        Organization organization = new Organization("Organization");
        organization.addContent(new Experience(YearMonth.parse("2010-01"), YearMonth.parse("2010-05"), "title", "description"));
        OrganizationSection organizationSection = new OrganizationSection();
        organizationSection.setContent(organization);
        assertEquals(organizationSection, testResume.getSection(SectionType.EXPERIENCE));
        organization = new Organization("Education");
        organization.addContent(new Experience(YearMonth.parse("2001-01"), YearMonth.parse("2001-05"), "title", "description"));
        organizationSection = new OrganizationSection();
        organizationSection.setContent(organization);
        assertEquals(organizationSection, testResume.getSection(SectionType.EDUCATION));
    }
}
