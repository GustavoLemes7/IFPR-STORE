package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.models.Department;
import br.edu.ifpr.foz.ifprstore.models.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRepository {

    private Connection connection;

    public DepartmentRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public List<Department> getDepartments() {

        List<Department> departments = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM department");

            while (result.next()) {

                Department department = instantiateDepartment(result);

                departments.add(department);

            }

            result.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return departments;
    }

    public Department update(Integer id, Department department) {

        String sql = "UPDATE department SET Name = ? WHERE Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, department.getName());
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Rows updated: " + rowsUpdated);
            } else {
                throw new DatabaseException("Departamento não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

        return department;
    }

    public Department insert(Department department) {

        String sql = "INSERT INTO department (Name) " +
                "VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, department.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer departmentId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + departmentId);

                department.setId(departmentId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return department;
    }



    public void delete(Integer id) {

        String sql = "DELETE FROM department WHERE Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            Integer rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public List<Department> getByNome(String string) {

        List<Department> departments = new ArrayList<>();


        String sql = "SELECT * " +
                "FROM department " +
                "WHERE department.Name like '%" + string + "%' ";

        try {


            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Department department = instantiateDepartment(resultSet);

                departments.add(department);

            }

            resultSet.close();

            if (departments.isEmpty()) {

                throw new DatabaseException("Departamento não encontrado");


            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return departments;
    }

    public Department getById(Integer id) {


        Department department;

        String sql = "SELECT * " +
                "FROM department " +
                "WHERE department.Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                department = this.instantiateDepartment(resultSet);


            } else {
                throw new DatabaseException("Departamento não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return department;
    }


    public List<Department> getDepartmentVazio() {

        List<Department> departments = new ArrayList<>();


        String sql = "SELECT * FROM department LEFT JOIN seller  ON department.id = seller.DepartmentId  WHERE seller.Id IS NULL";

        try {


            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Department department = instantiateDepartment(resultSet);

                departments.add(department);

            }

            resultSet.close();

            if (departments.isEmpty()) {

                throw new DatabaseException("Não há departamentos vazios");


            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return departments;
    }


    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {

        Department department = new Department();

        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));

        return department;
    }

}
