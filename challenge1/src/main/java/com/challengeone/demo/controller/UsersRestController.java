package com.challengeone.demo.controller;

import com.challengeone.demo.model.Users;
import com.challengeone.demo.model.db.UsersEntity;
import com.challengeone.demo.repository.UsersRepository;
import com.challengeone.demo.repository.criteria.UsersCriteria;
import com.challengeone.demo.repository.specification.UsersSpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UsersRestController {

    private final UsersRepository usersRepository;

    public UsersRestController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(
        @RequestParam(value = "sortBy", required = false) String sortByType,
        @RequestParam(value = "filter", required = false) String filter,
        @RequestParam(value = "start", required = false, defaultValue = "0") int start,
        @RequestParam(value = "limit", required = false, defaultValue = "100") int limit
        ) {

        Map<String, String> filterMap = new HashMap<>();

        if (StringUtils.isNotBlank(filter)) {

            /**
             * Validate the filter parameter to be of type lastname:value or age:value or lastname:value,age:value
             */
            if (!Pattern.matches("^([a-zA-Z]*:[a-zA-Z0-9]*(,)?)*$", filter)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid filter format.");
            }

            String[] filters = filter.split(",");
            for (String individualFilter : filters) {
                if (individualFilter.split(":")[0].equalsIgnoreCase("lastname")) {
                    filterMap.put("lastname", individualFilter.split(":")[1]);
                } else if (individualFilter.split(":")[0].equalsIgnoreCase("age")) {
                    filterMap.put("age", individualFilter.split(":")[1]);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid filter parameter.");
                }
            }
        }

        if (!StringUtils.isBlank(sortByType)) {

            List<String> allowedList = Arrays.asList("name", "-name", "age", "-age");
            if (!allowedList.contains(sortByType)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sort type");
            }
        }

        UsersCriteria usersCriteria = UsersCriteria.builder()
                                                     .limit(limit)
                                                     .start(start)
                                                     .sortKey(sortByType)
                                                     .filter(filterMap).build();

        List<UsersEntity> listOfUsers = null;
        if (limit == -1) {
            listOfUsers = usersRepository.findAll(UsersSpec.findUsersByCriteria(usersCriteria));

        } else {
            listOfUsers = usersRepository.findAll(UsersSpec.findUsersByCriteria(usersCriteria), PageRequest.of(start, limit)).getContent();
        }

        /**
         * Tranform the dbo to pojo object.
         */
        List<Users> userList = listOfUsers.stream().map(e ->
            new Users(e.getId(), e.getLastname()+", "+e.getFirstname(), e.getAge(), e.getAddress1(), e.getAddress2())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(userList);
    }

}
