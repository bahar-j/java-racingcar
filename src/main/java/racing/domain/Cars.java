package racing.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cars {

    private final List<Car> cars;
    private final int moveCount;

    public Cars(List<String> names, int moveCount) {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            cars.add(new Car(names.get(i)));
        }
        this.moveCount = moveCount;
        this.cars = Collections.unmodifiableList(cars); // 원본 리스트 대신 수정 불가능 리스트 세팅 -> 외부에서 변경 불가능
    }

    public Cars(int moveCount, List<Car> cars) {
        this.moveCount = moveCount;
        this.cars = Collections.unmodifiableList(cars);
    }

    public Cars move() {
        this.cars.forEach(car -> car.move(new RandomMovable()));
        return this;
    }

    public List<String> pickWinners() {
        return cars.stream()
                .filter(car -> car.isLocatedAt(maxPosition()))
                .map(Car::name)
                .map(Name::getValue)
                .collect(Collectors.toList());
    }

    private Position maxPosition() {
        return Position.max(cars.stream()
                .map(Car::position)
                .collect(Collectors.toList()));
    }

    public List<Car> values() {
        return cars;
    }

    public int moveCount() {
        return moveCount;
    }

}
