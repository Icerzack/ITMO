import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { countAllRoutesS1 } from '../../utils/apiInteraction';
import toast from 'react-hot-toast';
import get from 'lodash.get';

export const CountAllRoutes = () => {
  const countDistance = () => {
    toast.promise(countAllRoutesS1(), {
      loading: 'Вычисляем...',
      success: (response) => 'Итоговая distance: ' + response.data,
      error: (err) => get(err, 'response.data.message', 'Error!')
    });
  };

  return (
    <Form>
      <div className="horizontal-placer mt-3">
        <div className="optitle">Рассчитать сумму всех distance для всех Route</div>
        <Button variant="dark" className="button-2 form" onClick={countDistance}>
          Рассчитать
        </Button>
      </div>
    </Form>
  );
};
