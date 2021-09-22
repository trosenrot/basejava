package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ResumeTestData {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Resume resume;

    public static void main(String[] args) throws IOException {
        System.out.print("Введите полное имя: ");
        String name = reader.readLine();
        resume = new Resume(name);
        while (true) {
            System.out.print("Введите 1-4 для выбора действия: 1) Добавить контакты, 2) Добавить данные, 3) Вывести резюме, 4) Выход: ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length != 1) {
                System.out.println("Неверная команда.");
                continue;
            }
            switch (params[0]) {
                case "1":
                    saveContact();
                    break;
                case "2":
                    saveData();
                    break;
                case "3":
                    System.out.println(resume.toString());
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void saveContact() throws IOException {
        ContactType type;
        while (true) {
            System.out.println("Введите 1-4 для выбора: 1)Ввести телефон, 2)Ввести домашний телефон, 3)Ввести почту, ");
            System.out.println("4)Ввести профиль LinkedIn, 5)Ввести профиль GitHub, 6)Ввести профиль StackOverFlow, ");
            System.out.print("7) Ввести домашнюю страницу, 8)Вернуться назад: ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length != 1) {
                System.out.println("Неверная команда.");
                continue;
            }
            switch (params[0]) {
                case "1":
                    type = ContactType.PHONE;
                    inputContact(type);
                    break;
                case "2":
                    type = ContactType.HOME_PHONE;
                    inputContact(type);
                    break;
                case "3":
                    type = ContactType.E_MAIL;
                    inputContact(type);
                    break;
                case "4":
                    type = ContactType.LINKEDIN;
                    inputContact(type);
                    break;
                case "5":
                    type = ContactType.GITHUB;
                    inputContact(type);
                    break;
                case "6":
                    type = ContactType.STACKOVERFLOW;
                    inputContact(type);
                    break;
                case "7":
                    type = ContactType.HOME_PAGE;
                    inputContact(type);
                    break;
                case "8":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void inputContact(ContactType type) throws IOException {
        System.out.print("Введите " + type.getTitle() + ": ");
        String content;
        content = reader.readLine();
        resume.setContact(type, content);
    }

    private static void saveData() throws IOException {
        SectionType type;
        while (true) {
            System.out.println("Введите 1-4 для выбора: 1)Ввести личные качества, 2)Ввести позицию, 3)Ввести достижения, ");
            System.out.print("4)Ввести квалификацию, 5)Ввести опыт работы, 6)Ввести образование, 7)Вернуться назад: ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length != 1) {
                System.out.println("Неверная команда.");
                continue;
            }
            switch (params[0]) {
                case "1":
                    type = SectionType.PERSONAL;
                    inputSection(type);
                    break;
                case "2":
                    type = SectionType.OBJECTIVE;
                    inputSection(type);
                    break;
                case "3":
                    type = SectionType.ACHIEVEMENT;
                    inputSection(type);
                    break;
                case "4":
                    type = SectionType.QUALIFICATIONS;
                    inputSection(type);
                    break;
                case "5":
                    type = SectionType.EXPERIENCE;
                    inputSection(type);
                    break;
                case "6":
                    type = SectionType.EDUCATION;
                    inputSection(type);
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void inputSection(SectionType type) throws IOException {
        if (type == SectionType.PERSONAL || type == SectionType.OBJECTIVE) {
            inputTextSection(type);
        } else if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
            inputListSection(type);
        } else {
            inputOrganizationSection(type);
        }
    }

    private static void inputTextSection(SectionType type) throws IOException {
        System.out.print("Введите \"" + type.getTitle() + "\": ");
        String content = reader.readLine();
        resume.setSections(type, new TextSection(content));
    }

    private static void inputListSection(SectionType type) throws IOException {
        ListSection list = new ListSection();
        while (true) {
            System.out.print("Введите \"" + type.getTitle() + "\": ");
            list.setContent(reader.readLine());
            if (!continueInput()) {
                resume.setSections(type, list);
                return;
            }
        }
    }

    private static void inputOrganizationSection(SectionType type) throws IOException {
        OrganizationSection list = new OrganizationSection();
        while (true) {
            System.out.print("Введите \"" + type.getTitle() + "\": ");
            list.setContent(inputOrganization());
            if (!continueInput()) {
                resume.setSections(type, list);
                return;
            }
        }
    }

    private static Experience inputOrganization() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        System.out.print("Введите название организации: ");
        String name = reader.readLine();
        System.out.print("Введите начало работы/учебы в организации в формате MM/YYYY: ");
        YearMonth startDate = YearMonth.parse(reader.readLine(), formatter);
        System.out.print("Введите окончание работы/учебы в организации в формате MM/YYYY или \"Сейчас\": ");
        String date = reader.readLine();
        YearMonth endDate;
        endDate = (!date.equals("Сейчас")) ? YearMonth.parse(date, formatter) : null;
        System.out.print("Введите заголовок: ");
        String title = reader.readLine();
        System.out.print("Введите описание: ");
        String description = reader.readLine();
        return new Experience(name, startDate, endDate, title, description);
    }

    private static boolean continueInput() throws IOException {
        while (true) {
            System.out.print("Продолжить ввод? [д/н]: ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length != 1) {
                System.out.println("Неверная команда.");
                continue;
            }
            if (params[0].equals("д") || params[0].equals("Д")) {
                return true;
            } else if (params[0].equals("н") || params[0].equals("Н")) {
                return false;
            } else {
                System.out.println("Неверная команда.");
            }
        }
    }
}

