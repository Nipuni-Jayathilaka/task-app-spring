package lk.ijse.dep9.dao.custom.impl;

import lk.ijse.dep9.dao.custom.QueryDAO;
import lk.ijse.dep9.dao.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
@Component
public class QueryDAOImpl implements QueryDAO {
    @Autowired
    private Connection connection;


}
