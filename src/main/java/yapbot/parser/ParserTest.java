package yapbot.parser;

import org.junit.Test;
import yapbot.ui.UI;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ParserTest {
    String[][] TEST_TASKS = {
            {
                "[T][ ] todo",
                "[D][ ] deadline -by 22/11/2003",
                "[E][ ] event -from Mon -to Fri",
            },
            {
                "[T][X] todo",
                "[D][X] deadline -by 22/11/2003",
                "[E][X] event -from Mon -to Fri",
            },
            {
                "[T][ ] todo read book",
                "[D][ ] deadline return book -by 22/11/2003",
                "[E][ ] event -from Mon -to Fri",
            }
    };

    private void initTestTasks(int index)
    {
        UI.loadingFile();

        String[] tasks = TEST_TASKS[index];
        for (String task : tasks) {
            Parser.storeTask(task);
        }
        Parser.list();

        UI.loadedFile();
    }

    @Test
    public void testMark() {
        initTestTasks(0);

        String[] VALID_MARKS = {
                "[mark] 1",
                "[mark] 2",
                "[mark] 3",
        };
        for (String validMark : VALID_MARKS) {
            assertTrue(Parser.markTest(validMark));
        }
        Parser.list();

        String[] INVALID_MARKS = {
                "[mak]",
                "[mark]",
                "[mark] 0",
                "[mark] 4",
                "mark 1",
        };
        for (String invalidMark : INVALID_MARKS) {
            assertFalse(Parser.markTest(invalidMark));
        }
        Parser.list();
    }

    @Test
    public void testUnmark() {
        initTestTasks(1);

        String[] VALID_UNMARKS = {
                "[unmark] 1",
                "[unmark] 2",
                "[unmark] 3",
        };

        for (String validUnmark : VALID_UNMARKS) {
            assertTrue(Parser.unmarkTest(validUnmark));
        }
        Parser.list();

        String[] INVALID_UNMARKS = {
                "[unmak]",
                "[unmark]",
                "[unmark] 0",
                "[unmark] 4",
                "unmark 1",
        };
        for (String invalidUnmark : INVALID_UNMARKS) {
            assertFalse(Parser.unmarkTest(invalidUnmark));
        }
        Parser.list();
    }

    @Test
    public void testDelete() {
        initTestTasks(0);

        String[] VALID_DELETES = {
                "[delete] 3",
                "[delete] 2",
                "[delete] 1",
        };

        for (String validDelete : VALID_DELETES) {
            assertTrue(Parser.deleteTest(validDelete));
        }
        Parser.list();

        String[] INVALID_DELETES = {
                "[delet]",
                "[delete]",
                "[delete] 0",
                "[delete] 4",
                "delete",
        };
        for (String invalidDelete : INVALID_DELETES) {
            assertFalse(Parser.deleteTest(invalidDelete));
        }
        Parser.list();
    }

    @Test
    public void testFind()
    {
        initTestTasks(2);

        String[] VALID_FINDS = {
                "[find] todo",
                "[find] deadline",
                "[find] event",
                "[find] book",
                "[find] 22"
        };

        for (String validFind : VALID_FINDS) {
            assertTrue(Parser.findTest(validFind));
        }

        String[] INVALID_FINDS = {
                "[find] toodo",
                "[find] dedlin",
                "[find] evnt",
                "[find] boook",
        };
        for (String invalidFind : INVALID_FINDS) {
            assertFalse(Parser.findTest(invalidFind));
        }
    }
}