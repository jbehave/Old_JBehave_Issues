@RunWith(MockitoJUnitRunner.class)
public class JBehaveTest {

    @Test
    public void ConcurrentModificationToParameterConvertersThrowsException() {

        final ParameterConverters parameterConverters = new ParameterConverters();

        final boolean[] active = new boolean[]{true};
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (active[0]) {
                    parameterConverters.addConverters(new ParameterConverters.ParameterConverter() {
                        @Override
                        public boolean accept(Type type) {
                            return false;
                        }

                        @Override
                        public Object convertValue(String value, Type type) {
                            return null;
                        }
                    });
                }
            }
        });
        t.start();
        parameterConverters.convert("test", String.class);
        active[0] = false;
    }
}