package parallel;

import org.testng.annotations.Test;
import util.APIBaseState;

public class TestParallelExecution extends APIBaseState {

    @Test
    public void test1() {
        System.out.println("Test 1" + Thread.currentThread().getName());
    }

    @Test
    public void test2() {
        System.out.println("Test 2" + Thread.currentThread().getId());
    }

    @Test
    public void test3() {
        System.out.println("Test 3" + Thread.currentThread().getId());
    }
}
