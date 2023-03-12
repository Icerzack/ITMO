import * as React from "react";
import { Paper } from "@mui/material";
import Box from "@mui/material/Box";
import { Stack } from "@mui/system";
import { useParams } from "react-router-dom";
import { baaList } from "../../helpers/baa";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function BaaView(props) {
  const { id } = useParams();

  const [title, setTitle] = React.useState("");
  let [manufacturer, setManufacturer] = React.useState("");
  let [description, setDescription] = React.useState("");
  let [uses, setUses] = React.useState("");
  let [allTimeRatingDifference, setAllTimeRatingDifference] =
    React.useState("");
  const navigate = useNavigate();

  const list = baaList;

  useEffect(() => {
    const url = "http://localhost:32456/get/baa/" + id;
    axios({
      method: "get",
      url: url,
    })
      .then(function (response) {
        let temp = response.data.body;
        setTitle(temp[0].name);
        setDescription(temp[0].description);
        setManufacturer(temp[0].manufacturer);
        setUses(temp[0].uses);
        setAllTimeRatingDifference(temp[0].allTimeRatingDifference);
      })
      .catch(function (error) {
          navigate("/sportsman/baa");
      });
    // setTitle(baaList[0].name);
    // setDescription(baaList[0].description);
    // setManufacturer(baaList[0].manufacturer);
    // setUses(baaList[0].uses);
    // setAllTimeRatingDifference(baaList[0].allTimeRatingDifference);
  }, []);

  let h = window.innerHeight;
  let gridHeight = h - 60;

  return (
    <Box
      sx={{
        height: gridHeight,
      }}
    >
      <Box
        sx={{
          height: "100%",
          display: "flex",
          justifyContent: "center",
          alignItems: "left",
          flexDirection: "column",
          ml: "50px",
          mt: "80px",
        }}
      >
        <img
          src="https://avatars.mds.yandex.net/get-mpic/4905155/img_id3164344516613040350.jpeg/orig"
          width="200"
          height="300"
          alt="lorem"
        />
        <Box>
          <h1>{title}</h1>
          <h3>{manufacturer}</h3>
        </Box>
        <Box>
          <h2>Описание товара</h2>
        </Box>
        <Box sx={{ width: "700px", wordWrap: "break-word" }}>{description}</Box>
        <Box>
          <h3>Количество использований: {uses}</h3>
          <h3>Рейтинг: {allTimeRatingDifference}</h3>
        </Box>
      </Box>
    </Box>
  );
}
