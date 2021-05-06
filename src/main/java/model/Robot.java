package model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Robot {

    private int coordinateX;

    private int coordinateY;

    private Orientation orientation;

    private String instruction;

    private boolean isLost;

    public Robot(int coordinateX, int coordinateY, Orientation orientation, String instruction) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.orientation = orientation;
        this.instruction = instruction;
    }

}
