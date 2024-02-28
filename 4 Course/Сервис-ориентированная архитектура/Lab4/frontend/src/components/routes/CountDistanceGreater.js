import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { useState } from 'react';
import { countAllRoutesDistanceGreaterS1 } from '../../utils/apiInteraction';
import toast from 'react-hot-toast';
import get from 'lodash.get';

export const CountDistanceGreater = () => {
  const [distance, setDistance] = useState('');
  const countAllRoutes = () => {
    toast.promise(countAllRoutesDistanceGreaterS1(distance), {
      loading: 'Вычисляем...',
      success: (response) => 'Итоговое количество: ' + response.data,
      error: (err) => get(err, 'response.data.message', 'Error, проверьте поля!')
    });
  };

  return (
    <Form>
      <div className="optitle mt-3">
        Вернуть количество объектов, значение поля distance которых больше заданного.
      </div>
      <div className="horizontal-placer">
        <Form.Control
          type="number"
          min="1"
          value={distance}
          placeholder="Значение distance"
          className="form"
          onChange={(event) => setDistance(event.target.value)}
        />
        <Button variant="dark" className="button-rev-2 form" onClick={countAllRoutes}>
          Вычислить
        </Button>
      </div>
    </Form>
  );
};
