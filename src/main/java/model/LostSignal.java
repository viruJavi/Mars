package model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LostSignal {

    private int coordinateX;

    private int coordinateY;

    private Orientation orientation;


}
