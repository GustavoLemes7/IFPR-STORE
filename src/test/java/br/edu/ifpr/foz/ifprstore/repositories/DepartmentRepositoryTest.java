package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.models.Department;
import org.junit.jupiter.api.Test;
import java.util.List;

public class DepartmentRepositoryTest {

    @Test
    public void deveExibirUmaListaDeDepartments() {

        DepartmentRepository repository = new DepartmentRepository();

        List<Department> departments = repository.getDepartments();

        for (Department s : departments) {
            System.out.println(s);
        }
    }

    @Test
    public void deveInserirUmRegistroNaTabelaDepartment() {

        DepartmentRepository repository = new DepartmentRepository();

        Department departmentFake = new Department();
        departmentFake.setName("JogosBooks");


        Department department = repository.insert(departmentFake);

    }

    @Test
    public void deveDeletarUmDepartment() {

        DepartmentRepository repository = new DepartmentRepository();
        repository.delete(5);

    }

    @Test
    public void deveRetornarUmDepartmentPeloId() {

        DepartmentRepository repository = new DepartmentRepository();
        Department department = repository.getById(4);

        System.out.println("Departamento: --------");
        System.out.println(department);


    }

    @Test
    public void deveRetornarUmDepartmentPeloNome() {

        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departments = repository.getByNome("Books");

        for (Department s : departments) {
            System.out.println(s);
        }


    }

    @Test
    public void deveAtualizarDepartment() {

        DepartmentRepository repository = new DepartmentRepository();

        Department department = new Department();
        department.setName("JogosMusica");

        repository.update(10, department);


    }

    @Test
    public void deveRetornarUmDepartmentVazio() {

        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departments = repository.getDepartmentVazio();

        for (Department s : departments) {
            System.out.println(s);
        }


    }
}