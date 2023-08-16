public class AlunoDAO {
    // ... Outros métodos e atributos da classe ...

    // Método para verificar se já existe um aluno com o mesmo nome
    public boolean existeAlunoComNome(String nome) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE nome = ?";

        try (Connection connection = Persistencia.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Retorna true se houver alunos com o mesmo nome
                }
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Object obj) {
        Aluno aluno = (Aluno) obj;

        // Verificar se já existe um aluno com o mesmo nome
        if (existeAlunoComNome(aluno.getNome())) {
            throw new AlunoException("Já existe um aluno com esse nome");
        }

        String sql = " INSERT INTO "
                + " aluno(nome, sexo, idade, matricula, anoIngresso) "
                + " VALUES(?,?,?,?,?) ";
        try (Connection connection = Persistencia.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getSexo() + "");
            statement.setInt(3, aluno.getIdade());
            statement.setString(4, aluno.getMatricula());
            statement.setInt(5, aluno.getAnoIngresso());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
