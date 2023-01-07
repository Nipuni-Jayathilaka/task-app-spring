package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.dao.DAOFactory;
import lk.ijse.dep9.dao.DaoTypes;
import lk.ijse.dep9.dao.custom.UserDAO;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDAO= DAOFactory.getInstance().getDAO(null, DaoTypes.USER, UserDAO.class);
    }
}
