CREATE KEYSPACE cass_platform_data WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE cass_platform_data.location_md (
    parent_id int,
    loc_type_id int,
    verific_status int,
    assessment_year text,
    loc_id int,

    loc_name text,
    rf_infil_rate double,
    gw_specific_yield double,
    storage_coeff double,
    soil_type text,
    loc_assoc text,
    area text,
    crop_info text,
    canal_info text,
    artificial_wc text,
    water_bodies text,
    infil_info text,
    geo_info text,
    resource_dist text,
    other_rechargeable_areas text,
    population text,
    ind_consump text,
    irri_consump text,
    gw_dependency text,
    rf_data text,
    gw_data text,
    canal_discharge text,
    base_flow text,
    aquifer_info text,
    processed_status int,
    PRIMARY KEY ((parent_id, loc_type_id, verific_status, assessment_year), loc_id)
) WITH CLUSTERING ORDER BY (loc_id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';


CREATE TABLE cass_platform_data.gec_meta_data (
    parent_uuid uuid,
    assessment_year text,
    assessment_hierarchy text,
    computation_hierarchies set<text>,
    assessment_units map<text, frozen<map<text,text>>>,
    season_duration map<text, frozen<map<text,text>>>,
    canal_infiltration text,
    dependency_factor text,
    geological_info text,
    gw_infiltration text,
    infiltration_info text,
    PRIMARY KEY (parent_uuid, assessment_year)
) WITH bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';



INSERT INTO gec_meta_data(parent_uuid, assessment_year, assessment_hierarchy, computation_hierarchies, assessment_units, canal_infiltration, dependency_factor) VALUES ( 123e4567-e89b-12d3-a456-426655440000, '2012-2013', 'admin', {'admin', 'basin', 'political'}, {'anantapur':('village',1), 'srikakulam':('mandal',0)}, 'canal-infiltration', 'dependency-factor');

