import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { useState } from 'react';
import { deleteAllRoutesS1 } from '../../utils/apiInteraction';
import toast from 'react-hot-toast';
import get from 'lodash.get';

export const DeleteAllRoutes = () => {
  const [distance, setDistance] = useState('');
  const deleteAllRoutes = () => {
    toast.promise(deleteAllRoutesS1(distance), {
      loading: 'Удаляем...',
      success: () => 'Успешно удалено!',
      error: (err) => get(err, 'response.data.message', 'Error, проверьте поля!')
    });
  };

  return (
    <Form>
      <div className="optitle mt-2">Удалить все Routes, у которых distance равен заданному</div>
      <div className="horizontal-placer">
        <Form.Control
          type="number"
          min="1"
          value={distance}
          placeholder="Значение distance"
          className="form"
          onChange={(event) => setDistance(event.target.value)}
        />
        <Button variant="dark" className="button-2 form" onClick={deleteAllRoutes}>
          Удалить
        </Button>
      </div>
    </Form>
  );
};
