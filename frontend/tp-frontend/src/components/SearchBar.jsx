import React, {useContext, useMemo, useRef, useState} from "react";
import {LoggedUserContext} from "./LoggedUserContext.jsx";

export default function SearchBar({setSearchCriteria}) {
    const [isEditable, setIsEditable] = useState(true);

    const {loggedUser, setLoggedUser} = useContext(LoggedUserContext);

    const textRef = useRef('');
    const dateRef = useRef('');

    const handleSearchClick = async () => {
        if (textRef.current.value || dateRef.current.value) {
            setIsEditable(!isEditable);
            await saveSearchCriteria(textRef.current.value, dateRef.current.value);
        }
    };

    const handleDeleteClick = async () => {
        setIsEditable(!isEditable);
        textRef.current.value = '';
        dateRef.current.value = '';
        setSearchCriteria([])
    };

    async function saveSearchCriteria(text, date) {
        const newSearchCriteria = {
            idUser: loggedUser.id,
            textRecherche: text,
            dateRecherche: date,
            dateCreation: new Date().toISOString()
        };

        const searchCriteriaListJSON = localStorage.getItem("searchCriteriaList");
        let searchCriteriaList = [];
        try {
            searchCriteriaList = searchCriteriaListJSON ? JSON.parse(searchCriteriaListJSON) : [];
        } catch (error) {
            console.error("Erreur lors de la lecture de l'historique de recherche:", error);
            searchCriteriaList = [];
        }
        const criteria = searchCriteriaList.find(criteria =>
            criteria.textRecherche === newSearchCriteria.textRecherche &&
            criteria.dateRecherche === newSearchCriteria.dateRecherche
        );

        let criteriaId = null;
        if (!criteria) {
            criteriaId = await postCritere(newSearchCriteria, searchCriteriaList);
        } else {
            criteriaId = criteria.id;
        }

        const fetchUrl = `http://localhost:8080/criteres/${criteriaId}/nouvelles`;

        const fetchResponse = await fetch(fetchUrl);

        if (!fetchResponse.ok) {
            throw new Error(`Failed to fetch news IDs: ${fetchResponse.statusText}`);
        }

        // The response body is the List<Long> from your server
        const newsIdList = await fetchResponse.json();
        setSearchCriteria([newsIdList])
    }

    async function postCritere(newCritere, searchCriteriaList) {
        const url = `http://localhost:8080/criteres`;
        console.log(newCritere)
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newCritere)
            });

            if (response.ok || response.status === 201) {
                const createdCritere = await response.json();
                searchCriteriaList.push(createdCritere);

                localStorage.setItem("searchCriteriaList", JSON.stringify(searchCriteriaList));

                console.log(`Ajouter critere dans le serveur et localement.`);
                return createdCritere.id;
            } else {
                console.error(`Échec de la création de la critere. Statut HTTP: ${response.status}`);
                return null
            }

        } catch (error) {
            console.error(`Erreur réseau lors de la création de la critere :`, error);
            return null
        }
    }

    return (
        <>
            <div className="topnav">
                <div className="search-group">
                    <input
                        type="text"
                        disabled={!isEditable}
                        ref={textRef}
                        placeholder="Rechercher..."
                    />
                </div>

                <input type="date" disabled={!isEditable} ref={dateRef}></input>

                <button
                    className="icon-btn search-btn"
                    onClick={handleSearchClick}
                    disabled={!isEditable}
                >
                    🔍
                </button>

                <button
                    className="icon-btn delete-btn"
                    onClick={handleDeleteClick}
                    disabled={isEditable}
                >
                    ❌
                </button>
            </div>
        </>
    )
}