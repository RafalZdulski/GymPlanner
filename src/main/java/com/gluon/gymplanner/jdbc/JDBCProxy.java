package com.gluon.gymplanner.jdbc;

public class JDBCProxy {

    private static final JDBCProxy INSTANCE = new JDBCProxy();

    public static JDBCProxy getInstance(){return INSTANCE;}

    private ExerciseJDBC exerciseJDBC;

    private UserJDBC userJDBC;

    private JDBCProxy(){
        this.exerciseJDBC = new ExerciseJDBCImpl();
        this.userJDBC = new UserJDBCImpl();
    }

    public ExerciseJDBC getExerciseJDBC() {
        return this.exerciseJDBC;
    }

    public UserJDBC getUserJDBC() {
        return this.userJDBC;
    }
}
