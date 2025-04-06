package org.example;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;

import java.io.IOException;
import java.util.*;

public class ParquetWriteAnalysis {
    public static void main(String[] args) {
        List<String> columnNames = Arrays.asList("id", "name", "gender", "pass");
        List<ParquetDataType> columnTypes = Arrays.asList(ParquetDataType.INT32,
                ParquetDataType.STRING, ParquetDataType.INT32, ParquetDataType.BOOLEAN);

        List<List<String>> data = Arrays.asList(
                Arrays.asList("0", "Lancy", "0", "true"),
                Arrays.asList("1", "Lucas", "1", "false"),
                Arrays.asList("2", "Bella", "0", "true"),
                Arrays.asList("3", "Tom", "1", "true"),
                Arrays.asList("4", "Linda", "0", "false"));

        String outputDir = "./src/main/resources/testfile.parquet";
        Long startTime = System.currentTimeMillis();
        new ParquetWriteAnalysis().writeToParquet(data, columnNames, columnTypes, outputDir);
        Long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) / 1000.0 + " seconds");
    }

    public Schema buildSchema(List<String> columnNames, List<ParquetDataType> columnTypes) {
        SchemaBuilder.FieldAssembler<Schema> fields = SchemaBuilder.record("ParquetRecord").namespace("org.example").fields();
        for (int i = 0; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            ParquetDataType columnType = columnTypes.get(i);
            switch (columnType) {
                case STRING:
                    fields = fields.name(columnName).type().nullable().stringType().noDefault();
                    break;
                case INT32:
                    fields = fields.name(columnName).type().nullable().intType().noDefault();
                    break;
                case INT64:
                    fields = fields.name(columnName).type().nullable().longType().noDefault();
                    break;
                case FLOAT:
                    fields = fields.name(columnName).type().nullable().floatType().noDefault();
                    break;
                case DOUBLE:
                    fields = fields.name(columnName).type().nullable().doubleType().noDefault();
                    break;
                case BOOLEAN:
                    fields = fields.name(columnName).type().nullable().booleanType().noDefault();
                    break;
            }
        }
        return fields.endRecord();
    }

    public void writeToParquet(List<List<String>> data,  List<String> columnNames, List<ParquetDataType> columnTypes, String outputDir) {
        Schema schema = buildSchema(columnNames, columnTypes);

        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(new Path(outputDir))
                .withSchema(schema).withWriteMode(ParquetFileWriter.Mode.OVERWRITE).build()) {
            for (List<String> row : data) {
                GenericRecord record = new GenericData.Record(schema);
                int columnIndex = 0;
                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    ParquetDataType columnType = columnTypes.get(i);
                    String value = row.get(i);
                    switch (columnType) {
                        case STRING:
                            record.put(columnName, value);
                            break;
                        case INT32:
                            record.put(columnName, Integer.parseInt(value));
                            break;
                        case INT64:
                            record.put(columnName, Long.parseLong(value));
                            break;
                        case FLOAT:
                            record.put(columnName, Float.parseFloat(value));
                            break;
                        case DOUBLE:
                            record.put(columnName, Double.parseDouble(value));
                            break;
                        case BOOLEAN:
                            record.put(columnName, Boolean.parseBoolean(value));
                            break;
                    }
                }
                writer.write(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
