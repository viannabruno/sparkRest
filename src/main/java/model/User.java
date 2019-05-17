package model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {



    private Long id;
    private String name;
    private Integer age;

   // public Long getId() {
     //   return id;
    //}

    //public void setId(Long id) {
    //    this.id = id;
    //}


}
